package info.pishen.gameoflife;

import com.lexicalscope.jewel.cli.Option;

public interface CLIOptions {
	@Option
	int getEval();
	
	boolean isEval();
	
	@Option
	int getSize();
	
	boolean isSize();
}
