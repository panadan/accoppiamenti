import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class SoluzioneTableModel extends AbstractTableModel {
	private ArrayList<Soluzione> soluzioni;
		
	public SoluzioneTableModel(ArrayList<Soluzione> soluzioni) {
		super();
		this.soluzioni = soluzioni;
	}

	@Override
	public int getRowCount() {
		return soluzioni.size();
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

    @Override
    public String getColumnName(int column) {
        String name = "??";
        switch (column) {
            case 0:
                name = "#";
                break;
            case 1:
                name = "Sfrindo";
                break;
            case 2:
                name = "Cambi Verga";
                break;
            case 3:
                name = "Costo";
                break;
        }
        return name;
    }

	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Soluzione s = soluzioni.get(rowIndex);
		Object value = null;

        switch (columnIndex) {
            case 0:
                value = rowIndex + 1;
                break;
            case 1:
                value = s.getSfrido(); 
                break;
            case 2:
                value = s.getCambiVerga();
                break;
            case 3:
                value = s.getCosto();
                break;
        }		
        return value;
	}

}
