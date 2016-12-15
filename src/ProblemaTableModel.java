import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class ProblemaTableModel extends AbstractTableModel {
	
	private ArrayList<Problema> passaggi;

	public ProblemaTableModel(ArrayList<Problema> passaggi) {
		super();
		this.passaggi = passaggi;
	}

	public ProblemaTableModel() {
		super();
		//this.accoppiamenti = new ArrayList<Accoppiamento>();
		this.passaggi = new ArrayList<Problema>();
	}
	
	@Override
	public int getRowCount() {
		return passaggi.size();
	}
	
	

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 3;
	}

    @Override
    public String getColumnName(int column) {
        String name = "??";
        switch (column) {
            case 0:
                name = "#";
                break;
            case 1:
                name = "Barcode";
                break;
            case 2:
                name = "Verghe";
                break;
        }
        return name;
    }

	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Problema p = passaggi.get(rowIndex);
		Object value = null;

        switch (columnIndex) {
            case 0:
                value = rowIndex + 1;
                break;
            case 1:
                value = p.getBarcodes(); 
                break;
            case 2:
                value = p.getVerghe();
                break;
        }		
        return value;
				
	}

}
