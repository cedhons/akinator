package Data;

import java.io.IOException;

public interface BankDirectory {
	
	public String[] listFileNames();
	
	public void loadBankFile(String fileName) throws IOException, ParsingError;
	
	public TargetReader getTargetReader();
	
	public TargetWriter getTargetWriter();

	public boolean contains(String name);

	public void addBank(String name);
}
