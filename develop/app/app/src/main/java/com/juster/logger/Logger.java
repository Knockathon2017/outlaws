/*
 * Copyright (c) 2016. Exzeo Software Pvt. Ltd.
 */

package com.juster.logger;

import android.util.Log;

import com.juster.BuildConfig;

/**
 * Created by Anurag Singh on 18/5/16 6:47 PM.
 */
public class Logger {

	public static final boolean IS_LOG_ENABLED = BuildConfig.DEBUG;

	/**
	 * Send a  log message.
	 *
	 * @param tag Used to identify the source of a log message.  It usually identifies
	 *            the class or activity where the log call occurs.
	 * @param msg The message you would like logged.
	 */
	public static void d(String tag, String msg){
		if (IS_LOG_ENABLED)
			Log.d(tag, msg);
	}

}
