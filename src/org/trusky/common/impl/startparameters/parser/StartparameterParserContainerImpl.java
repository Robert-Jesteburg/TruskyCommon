package org.trusky.common.impl.startparameters.parser;

import org.trusky.common.api.startparameters.parser.CommandLine;
import org.trusky.common.api.startparameters.parser.EditableStartparameterParserContainer;
import org.trusky.common.api.startparameters.parser.StartOptionParser;

import java.util.ArrayList;
import java.util.Optional;

public class StartparameterParserContainerImpl implements EditableStartparameterParserContainer {

	private final ArrayList<StartOptionParser> parserList = new ArrayList<>();

	@Override
	public int getParserCount() {
		return parserList.size();
	}

	@Override
	public StartOptionParser getParser(int index) {
		return parserList.get(index);
	}


	@Override
	public void addParser(StartOptionParser parser) {

		if (parser == null) {
			throw new IllegalArgumentException("The parser must not be NULL.");
		}

		parserList.add(parser);
	}

	@Override
	public Optional<StartOptionParser> findParserForOption(CommandLine cmdLine) {

		return parserList.stream()
				.filter(p -> p.canParse(cmdLine))
				.findFirst();
	}

}
