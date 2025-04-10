/*
 * Copyright (c) 2025 by Robert Niemann, Rehkamp 21a; 12266 Jesteburg, Germany.
 * Diese Software unterliegt der GPL v3 vorbehaltlich jedweder böswilliger Veränderungen, eingeschlossen aber nicht
 * abschließend:
 * a) Ausspionieren von Gesundheitsdaten (bspw. durch Weiterleiten dieser Informationen oder Auswertung der Daten auf
 *  bereitgestellten Servern)
 * b) Ausspionieren von Beziehungen zwischen Personen oder deren zeitliche Verläufe
 * c) Irreführung durch Angabe bewusst falscher Daten
 */

package org.trusky.common.impl.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.trusky.common.api.injection.InjectorFactory;
import org.trusky.common.api.logging.CommonLogger;

import java.text.MessageFormat;

public class CommonLoggerImpl implements CommonLogger {

	private static boolean isLoggingPrepared = false;

	private final CommonFormatStringRewriter formatStringRewriter;
	private final Logger logger;

	public CommonLoggerImpl(Class<?> cls) {
		this(cls, InjectorFactory.getInstance(CommonFormatStringRewriter.class));
	}

	public CommonLoggerImpl(Class<?> cls, CommonFormatStringRewriter formatStringRewriter) {
		this(cls, formatStringRewriter, LogManager.getLogger(cls));
	}


	public CommonLoggerImpl(Class<?> cls, CommonFormatStringRewriter formatStringRewriter, Logger logger) {
		this.formatStringRewriter = formatStringRewriter;
		this.logger = logger;
	}


	public static void setIsLoggingPrepared(boolean isLoggingPrepared) {
		CommonLoggerImpl.isLoggingPrepared = isLoggingPrepared;
	}


	@Override
	public void info(String message) {

		if (!isLoggingPrepared) {
			System.out.println(message);
		} else {
			logger.info(message);
		}

	}

	@Override
	public void info(String format, Object... args) {

		if (!isLoggingPrepared) {
			sysout(format, args);
		} else {
			logger.info(format, args);
		}
	}


	@Override
	public void debug(String message) {

		if (!isLoggingPrepared) {
			System.out.println(message);
		} else {
			logger.debug(message);
		}
	}

	@Override
	public void debug(String format, Object... args) {

		if (!isLoggingPrepared) {
			sysout(format, args);
		} else {
			logger.debug(format, args);
		}
	}

	@Override
	public void debug(String message, Exception e) {

		if (!isLoggingPrepared) {
			sysout(message, e);
		} else {
			logger.debug(message, e);
		}

	}

	@Override
	public void trace(String message) {

		if (!isLoggingPrepared) {
			System.out.println(message);
		} else {
			logger.trace(message);
		}
	}

	@Override
	public void trace(String format, Object... args) {

		if (!isLoggingPrepared) {
			sysout(format, args);
		} else {
			logger.trace(format, args);
		}

	}

	@Override
	public void trace(String message, Exception e) {

		if (!isLoggingPrepared) {
			sysout(message, e);
		} else {
			logger.debug(message, e);
		}

	}

	@Override
	public void warn(String message) {

		if (!isLoggingPrepared) {
			System.err.println(message);
		} else {
			logger.warn(message);
		}
	}

	@Override
	public void warn(String format, Object... args) {

		if (!isLoggingPrepared) {
			syserr(format, args);
		} else {
			logger.warn(format, args);
		}
	}


	@Override
	public void warn(String message, Exception e) {

		if (!isLoggingPrepared) {
			syserr(message, e);
		} else {
			logger.warn(message, e);
		}

	}

	@Override
	public void error(String message) {

		if (!isLoggingPrepared) {
			System.err.println(message);
		} else {
			logger.error(message);
		}

	}

	@Override
	public void error(String format, Object... args) {

		if (!isLoggingPrepared) {
			syserr(format, args);
		} else {
			logger.error(format, args);
		}
	}

	@Override
	public void error(String message, Exception e) {

		if (!isLoggingPrepared) {
			syserr(message, e);
		} else {
			logger.error(message, e);
		}
	}

	private void sysout(String log4jFormatString, Object[] args) {
		String message = MessageFormat.format(formatStringRewriter.fromLog4JToNormalString(log4jFormatString), args);
		System.out.println(message);
	}

	private void sysout(String message, Exception e) {

		StackTraceElement[] stackTraceArray = e.getStackTrace();
		System.out.print(message);
		System.out.print(": ");
		System.out.println(e.getMessage());
		for (StackTraceElement element : stackTraceArray) {
			System.out.println("\t" + element.toString());
		}
	}

	private void syserr(String log4jFormatString, Object[] args) {
		String message = MessageFormat.format(formatStringRewriter.fromLog4JToNormalString(log4jFormatString), args);
		System.err.println(message);
	}

	private void syserr(String message, Exception e) {

		StackTraceElement[] stackTraceArray = e.getStackTrace();
		System.err.print(message);
		System.err.print(": ");
		System.err.println(e.getMessage());
		for (StackTraceElement element : stackTraceArray) {
			System.err.println("\t" + element.toString());
		}
	}

}
