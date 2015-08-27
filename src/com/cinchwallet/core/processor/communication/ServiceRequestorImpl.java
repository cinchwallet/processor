package com.cinchwallet.core.processor.communication;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.core.ReConfigurable;
import org.jpos.q2.QBeanSupport;
import org.jpos.util.NameRegistrar;

public class ServiceRequestorImpl extends QBeanSupport implements  IServiceRequestor ,ReConfigurable,ServiceRequestorImplMBean {

    private static Logger logger;
    Configuration cfg;
    private IRequestDispatcher[] requestDispatcher;
    private Task[] tasks;
    private LinkedList<Runnable> queue;
    private ThreadPoolExecutor queueManager;
    private int sessions;

    private Logger auditLoggerIn = null;
    private Logger auditLoggerOut = null;
    private boolean auditEnabled = false;

    public ServiceRequestorImpl(){}

    public String getAvailableTaskCount() {
		if(queue == null)
			return "";
		else
			return queue.size()+"";
	}

	public String getActiveTaskThreadPoolCount()
	{
		if(queueManager == null)
			return "";
		else
			return queueManager.getActiveCount()+"";
	}

	public String getAvailableTaskThreadPoolCount()
	{
		if(queueManager == null)
			return "";
		else
			return queueManager.getPoolSize()+"";
	}

	public String getSessionsCount()
	{
		return sessions+"";
	}

	//TODO get from Request Dispatcher
	public String getHost() {
		return "";
	}

	//TODO get from Request Dispatcher
	public String getURL() {
		return "";
	}

	// TODO get from Request Dispatcher
	public int getPort(){
		return -1;
	}

    public void requestNoWait(Serializable request, long timeout, IAsyncCallback callBackHandler) throws Exception {
        if (callBackHandler == null) {
            throw new RuntimeException("IAsyncCallback handler can't be null");
        }
        process(request, timeout, callBackHandler, true);
    }

    public Serializable request(Serializable request, long timeout) throws Exception {
        return process(request, timeout, null, false);
    }

    private Serializable process(Serializable request, long timeout, IAsyncCallback callBackHandler, boolean noWait)
            throws Exception {
        if (timeout < 0) {
            throw new RuntimeException("Invalid timeout value");
        }
        Serializable response = null;
        Task task;
        synchronized (queue) {
            while (queue.isEmpty()) {
                try {
                    queue.wait();
                } catch (InterruptedException ignored) {
                }
            }
            task = (Task) queue.removeFirst();
        }
        if (!noWait) {
            try {
                synchronized (task.lock) {
                    task.setRequest(request);
                    task.setCallBackHandler(null);
                    task.setTimeout(timeout);
                    queueManager.execute(task);
                    try {
                        task.lock.wait(timeout);
                    } catch (InterruptedException ignored) {
                    }
                    response = task.getResponse();
                }
            } finally {
                task.finish();
            }
        } else {
            task.setRequest(request);
            task.setTimeout(timeout);
            task.setCallBackHandler(callBackHandler);
            queueManager.execute(task);
        }
        return response;
    }

    private void add(Runnable r) {
        if (r != null) {
            synchronized (queue) {
            	if(!queue.contains(r))
                {
	                queue.addLast(r);
	                queue.notify();
                }
            }
        }
    }

    public void setConfiguration(Configuration cfg) throws ConfigurationException {
        try {
            super.setConfiguration(cfg);
            this.cfg = cfg;
            sessions = Integer.parseInt(cfg.get("sessions", "10"));
            if (queue == null) {
                queue = new LinkedList<Runnable>();
            } else {
                synchronized (queue) {
                    queue.clear();
                }
            }
            shutdownThreads();
            queueManager = new ThreadPoolExecutor(sessions, sessions, Long.MAX_VALUE, TimeUnit.MILLISECONDS,
                    new java.util.concurrent.SynchronousQueue());
            queueManager.prestartAllCoreThreads();
            requestDispatcher = new IRequestDispatcher[sessions];
            tasks = new Task[sessions];
            for (int i = 0; i < sessions; i++) {
                requestDispatcher[i] = (IRequestDispatcher) (Class.forName(cfg.get("request-dispatcher").trim()))
                        .newInstance();
                tasks[i] = new Task();
                requestDispatcher[i].setConfiguration(cfg);
                tasks[i].setExecutableTask(requestDispatcher[i]);
                add(tasks[i]);
            }
            auditEnabled = Boolean.parseBoolean(cfg.get("enable-audit-log", "false"));
            if (auditEnabled) {
                String loggerName = cfg.get("audit-logger");
                String inLoggerName = cfg.get("audit-input-logger", loggerName);
                String outLoggerName = cfg.get("audit-output-logger", loggerName);
                if (loggerName == null || loggerName.length() == 0) {
                    if (inLoggerName == null || inLoggerName.length() == 0) {
                        throw new ConfigurationException(
                                "com.bhn.beas.core.issuer.communication.ServiceRequestorImpl: "
                                        + "Property 'audit-input-logger' is missing. "
                                        + "Either 'audit-logger' or 'audit-input-logger' should be specified.");
                    }
                    if (outLoggerName == null || outLoggerName.length() == 0) {
                        throw new ConfigurationException(
                                "com.bhn.beas.core.issuer.communication.ServiceRequestorImpl: "
                                        + "Property 'audit-output-logger' is missing. "
                                        + "Either 'audit-logger' or 'audit-output-logger' should be specified.");
                    }
                }
                auditLoggerIn = org.apache.log4j.Logger.getLogger(inLoggerName);
                auditLoggerOut = org.apache.log4j.Logger.getLogger(outLoggerName);
            }
            NameRegistrar.register(getName(), this);
        } catch (Exception _ex) {
            throw new ConfigurationException(_ex.getMessage());
        }
    }

    public void startService() {
        getLog4j().debug("ServiceRequestorImpl:startService()::" + getName() + " started");
    }

    public void stopService() {
        getLog4j().debug("ServiceRequestorImpl:stopService()::" + getName() + " stopped");
        NameRegistrar.unregister(getName());
        shutdownThreads();
    }

    private void shutdownThreads() {
        if (queueManager != null) {
            queueManager.shutdown();
            queueManager = null;
        }
    }

    private Logger getLog4j() {
        if (logger == null)
            logger = Logger.getLogger(ServiceRequestorImpl.class);
        return logger;
    }

    private class Task implements Runnable {

        private volatile boolean isWorking;
        private volatile boolean returnedToPool = false;
        private IRequestDispatcher executableTask;
        private Serializable request;
        private Serializable response;
        private Object lock = new Object();
        private IAsyncCallback callBackHandler;
        private long timeout;

        public void run() {

            synchronized (lock) {
                isWorking = true;
                response = null;
                returnedToPool = false;
                Throwable exception = null;

                try {
                    if (auditEnabled) {
                        auditLoggerOut.info(executableTask.getRequestAuditLog(request));
                    }
                } catch (Exception _ex) {
                    getLog4j().error(
                            "ServiceRequestorImpl:Task Run()::" + executableTask.getClass()
                                    + " does not implement getRequestAuditLog properly.", _ex);
                }

                try {
                    response = executableTask.execute(request, timeout);
                } catch (Throwable _tw) {
                    exception = _tw;
                    getLog4j().debug("ServiceRequestorImpl:Task Run()::" + getName() + " Exception", _tw);
                } finally {
                    isWorking = false;
                }

                try {
                    if (auditEnabled) {
                        auditLoggerIn.info(executableTask.getResponseAuditLog(request, response));
                    }
                } catch (Exception _ex) {
                    getLog4j().error(
                            "ServiceRequestorImpl:Task Run()::" + executableTask.getClass()
                                    + " does not implement getRequestAuditLog properly.", _ex);
                }

                if (callBackHandler != null) {
                    AsyncResult ar = new AsyncResult();
                    ar.request = request;
                    ar.response = response;
                    ar.exception = exception;
                    callBackHandler.onCompletion(ar);
                }
                lock.notify();
            }
            finish();
        }

        public  synchronized void finish() {
            if (!isWorking && !returnedToPool) {
                add(this);
                returnedToPool = true;
            }
        }

        public boolean isWorking() {
            return isWorking;
        }

        public IRequestDispatcher getExecutableTask() {
            return executableTask;
        }

        public void setExecutableTask(IRequestDispatcher executableTask) {
            this.executableTask = executableTask;
        }

        public Serializable getRequest() {
            return request;
        }

        public void setRequest(Serializable request) {
            this.request = request;
        }

        public Serializable getResponse() {
            return response;
        }

        public void setResponse(Serializable response) {
            this.response = response;
        }

        public IAsyncCallback getCallBackHandler() {
            return callBackHandler;
        }

        public void setCallBackHandler(IAsyncCallback callBackHandler) {
            this.callBackHandler = callBackHandler;
        }

        public long getTimeout() {
            return timeout;
        }

        public void setTimeout(long timeout) {
            this.timeout = timeout;
        }
    }


}