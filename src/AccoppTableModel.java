import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class AccoppTableModel extends AbstractTableModel {

	//private ArrayList<Accoppiamento> accoppiamenti;
	private ArrayList<Problema> passaggi;

//	public AccoppTableModel(ArrayList<Accoppiamento> accoppiamenti) {
//		super();
//		this.accoppiamenti = new ArrayList<>(accoppiamenti);
//	}

	public AccoppTableModel(ArrayList<Problema> passaggi) {
		super();
		this.passaggi = new ArrayList<Problema>(passaggi);
	}

	public AccoppTableModel() {
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
		return 9;
	}

    @Override
    public String getColumnName(int column) {
        String name = "??";
        switch (column) {
            case 0:
                name = "Verga";
                break;
            case 1:
                name = "Bcode1";
                break;
            case 2:
                name = "Bcode2";
                break;
            case 3:
                name = "Qt1";
                break;
            case 4:
                name = "Qt2";
                break;
            case 5:
                name = "QtV";
                break;
            case 6:
                name = "USpz";
                break;
            case 7:
                name = "SfrV";
                break;
            case 8:
                name = "SfrT";
                break;
               
        }
        return name;
    }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            Class type = String.class;
            switch (columnIndex) {
                case 3:
                case 4:
                case 5:
                    type = Integer.class;
                    break;
                case 6:
                case 7:
                case 8:
                    type = Float.class;
                    break;
            }
            return type;
        }

	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		//Accoppiamento a = accoppiamenti.get(rowIndex);
		Accoppiamento a = passaggi.get(rowIndex).getAccoppiamento();
		Object value = null;

		if(a == null) return value;
		
            switch (columnIndex) {
                case 0:
                    value = a.getVerga().getNome();
                    break;
                case 1:
                    value = a.getBarcodeX().getNome() + " x" + a.getMulX(); 
                    break;
                case 2:
 					if (a.getBarcodeY().getNome().equals("vuoto")) return value;              
                    value = a.getBarcodeY().getNome() + " x" + a.getMulY();
                    break;
                case 3:
                    value = a.getTotQtaX();
                    break;
                case 4:
                    value = a.getTotQtaY();
                    break;
                case 5:
                    value = a.getTotVergheInetere();
                    break;
                case 6:
                    value = a.getUltimoSpezzone().getLunghezza();
                    break;
                case 7:
                    value = a.getSfridoSingolo();
                    break;
                case 8:
                    value = a.getSfrido();
                    break;
            }		
		return value;
	}

}
