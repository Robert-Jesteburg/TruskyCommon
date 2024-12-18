package org.trusky.common.api.startparameters.parser;

/**
 * Container to hold all the defined parsers.
 */
public interface StartparameterParserContainer {

	public int getParserCount();

	public StartOptionParser getParser(int index);
}
