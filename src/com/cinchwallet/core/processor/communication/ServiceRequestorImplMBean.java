package com.cinchwallet.core.processor.communication;

import org.jpos.q2.QBeanSupportMBean;

public interface ServiceRequestorImplMBean extends QBeanSupportMBean{

	public abstract String getAvailableTaskCount();
	public abstract String getActiveTaskThreadPoolCount();
	public abstract String getAvailableTaskThreadPoolCount();
	public abstract String getSessionsCount();
	public abstract String getHost();
	public abstract int getPort();
	public abstract String getURL();
}
