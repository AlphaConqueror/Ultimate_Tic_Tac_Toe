package uttt.utils;

public enum Symbol {
	EMPTY(" ", 0), CROSS("X", 1), CIRCLE("O", 2);

	private String name;
	private int index;

	Symbol(String name, int index) {
		this.name = name;
		this.index = index;
	}

	@Override
	public String toString() {
		return name;
	}

	public int getIndex() {
		return index;
	}

	public static Symbol valueOf(int index) {
		for(Symbol symbol : Symbol.values())
			if(symbol.getIndex() == index)
				return symbol;

		return null;
	}

	public Symbol flip() {
		if (this == CROSS)
			return CIRCLE;
		if (this == CIRCLE)
			return CROSS;
		return EMPTY;
	}
}
