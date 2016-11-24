import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.util.ArrayList;
import java.util.jar.Attributes.Name;


public class RisolutorGui {

	private JFrame frmRisolutoreTaglio;
	private JTextField textPresa;
	private JTextField textTaglio;
	private JTextField textCostoMat;
	private JTextField textCostoPiaz;
	private JTable tblVerghe;
	private JTable tblBarcodes;
	private JTextArea textAreaSoluzioni;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
			        UIManager.setLookAndFeel(
			        		UIManager.getSystemLookAndFeelClassName());
					RisolutorGui window = new RisolutorGui();
					window.frmRisolutoreTaglio.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	/**
	 * Create the application.
	 */
	public RisolutorGui() {
		initialize();
		myInit();
	}

	private void myInit() {
		textCostoPiaz.setText("10");
		textCostoMat.setText("2");
		textPresa.setText("32");
		textTaglio.setText("2");
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRisolutoreTaglio = new JFrame();
		frmRisolutoreTaglio.setTitle("Risolutore Taglio");
		frmRisolutoreTaglio.setBounds(100, 100, 700, 492);
		frmRisolutoreTaglio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		frmRisolutoreTaglio.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		splitPane.setLeftComponent(panel);
		panel.setLayout(new MigLayout("", "[grow 50][][pref!][90px:n][pref!][grow 50][grow][][]", "[][][grow][][][100px:n,grow][][][][]"));
		
		JLabel lblProblema = new JLabel("Definizione Problema");
		lblProblema.setFont(lblProblema.getFont().deriveFont(lblProblema.getFont().getStyle() | Font.BOLD));
		panel.add(lblProblema, "cell 0 0 7 1,alignx center");
		
		JLabel lblOffset = new JLabel("Offset:");
		panel.add(lblOffset, "cell 1 1,alignx trailing");
		
		JLabel lblCosto = new JLabel("Costo:");
		panel.add(lblCosto, "cell 3 1,alignx trailing");
		
		JLabel lblBarcodes = new JLabel("Barcodes:");
		panel.add(lblBarcodes, "cell 6 1");
		
		JLabel lblOffsetPresamm = new JLabel("Presa (mm)");
		panel.add(lblOffsetPresamm, "cell 1 2,alignx trailing");
		
		textPresa = new JTextField();
		panel.add(textPresa, "cell 2 2,growx");
		textPresa.setColumns(4);
		
		JLabel lblVergamt = new JLabel("Materiale al Mt");
		panel.add(lblVergamt, "cell 3 2,alignx trailing");
		
		textCostoMat = new JTextField();
		panel.add(textCostoMat, "cell 4 2,growx");
		textCostoMat.setColumns(6);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel.add(scrollPane_1, "cell 6 2 1 4,grow");
		
		tblBarcodes = new JTable();
		tblBarcodes.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"Nome", "Qttb (mm)", "Qta"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Float.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tblBarcodes.getColumnModel().getColumn(1).setMaxWidth(75);
		tblBarcodes.getColumnModel().getColumn(2).setMaxWidth(75);
		scrollPane_1.setViewportView(tblBarcodes);
		
		JLabel lblTagliomm = new JLabel("Taglio (mm)");
		panel.add(lblTagliomm, "cell 1 3,alignx trailing");
		
		textTaglio = new JTextField();
		panel.add(textTaglio, "cell 2 3,growx");
		textTaglio.setColumns(4);
		
		JLabel lblPiazzamento = new JLabel("Piazzamento");
		panel.add(lblPiazzamento, "cell 3 3,alignx trailing");
		
		textCostoPiaz = new JTextField();
		panel.add(textCostoPiaz, "cell 4 3,growx");
		textCostoPiaz.setColumns(6);
		
		JLabel lblVerghe = new JLabel("Verghe:");
		panel.add(lblVerghe, "cell 0 4,alignx leading");
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, "cell 0 5 6 1,grow");
		
		tblVerghe = new JTable();
		tblVerghe.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
				{null, null, null},
			},
			new String[] {
				"Nome", "Lunghezza (mm)", "Qta"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Float.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		tblVerghe.getColumnModel().getColumn(1).setMaxWidth(75);
		tblVerghe.getColumnModel().getColumn(2).setMaxWidth(75);
		scrollPane.setViewportView(tblVerghe);
		
		JButton btnClearVerghe = new JButton("Pulisci Verghe");
		btnClearVerghe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel.add(btnClearVerghe, "cell 0 6 6 1,alignx center");
		
		JButton btnClearBarcodes = new JButton("Pulisci Barcodes");
		panel.add(btnClearBarcodes, "cell 6 6,alignx center");
		
		JButton btnRisolvi = new JButton("Risolvi");
		btnRisolvi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				risolvi();
			}
		});
		panel.add(btnRisolvi, "cell 0 8 7 1,alignx center");
		
		JPanel panel_1 = new JPanel();
		splitPane.setRightComponent(panel_1);
		panel_1.setLayout(new MigLayout("", "[grow]", "[][grow]"));
		
		JLabel lblSoluzioni = new JLabel("Soluzioni");
		lblSoluzioni.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_1.add(lblSoluzioni, "cell 0 0,alignx center");
		
		JScrollPane scrollPane_2 = new JScrollPane();
		panel_1.add(scrollPane_2, "cell 0 1,grow");
		
		textAreaSoluzioni = new JTextArea();
		scrollPane_2.setViewportView(textAreaSoluzioni);
	}

	protected void risolvi() {
		try {
			ArrayList<Verga> verghe = getVerghe();
			ArrayList<Barcode> barcodes = getBarcodes();
			
			
			
			Risolutore r = new Risolutore(new Problema(barcodes,verghe), getOffsetPinza(), getOffsetTaglio(), getCostoMetro(), getPiazzamento());
			r.risolvi();
			textAreaSoluzioni.setText(r.stampaSoluzioni(10));
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Errore!", JOptionPane.INFORMATION_MESSAGE);
		}
		
		
		
		
		
	}

	private Float getPiazzamento() {
		// TODO Auto-generated method stub
		return new Float(textCostoPiaz.getText());
	}

	private Float getCostoMetro() {
		// TODO Auto-generated method stub
		return new Float(textCostoMat.getText());
	}

	private Float getOffsetPinza() {
		// TODO Auto-generated method stub
		return new Float(textPresa.getText());
	}

	private Float getOffsetTaglio() {
		// TODO Auto-generated method stub
		return new Float(textTaglio.getText());
	}

	private ArrayList<Verga> getVerghe() throws Exception {
		DefaultTableModel model = (DefaultTableModel) tblVerghe.getModel();
		ArrayList<Verga> verghe = new ArrayList<Verga>();
		
		  for(int count = 0; count < model.getRowCount(); count++){
			  Float lunghezza = (Float) model.getValueAt(count, 1);
			  if(lunghezza == null) continue; //Se non c'è la lunghezza non lo considero
			  Integer qta = (Integer) model.getValueAt(count, 2);
			  if(qta == null){
				  qta = Integer.MAX_VALUE;
				  model.setValueAt(qta, count, 2);
			  }
			  
			  if(qta <= 0) throw new Exception("Nessuna verga può avere quantità negativa");
			  if(lunghezza <= 0) throw new Exception("Nessuna Verga può avere lunghezza minore di zero");

			  
			  String nome = (String) model.getValueAt(count, 0);
			  if(nome == null || nome.isEmpty()) {
				  nome = "verga" + (count+1);
				  model.setValueAt(nome, count, 0);
			  }
			  
			  for(Verga v : verghe){
				 if(v.getNome().equals(nome)) throw new Exception("Due Verghe non possono avere lo stesso nome"); 
			  }
	  
			  verghe.add(new Verga(nome,lunghezza,qta));
		  }
		return verghe;
	}

	private ArrayList<Barcode> getBarcodes() throws Exception {
		DefaultTableModel model = (DefaultTableModel) tblBarcodes.getModel();
		ArrayList<Barcode> barcodes = new ArrayList<Barcode>();
		
		  for(int count = 0; count < model.getRowCount(); count++){
			  Float lunghezza = (Float) model.getValueAt(count, 1);
			  Integer qta = (Integer) model.getValueAt(count, 2);
			  String nome = (String) model.getValueAt(count, 0);
			  
			  if(lunghezza == null && (nome == null || nome.isEmpty()) && qta == null) continue; //Se non c'è la lunghezza non lo considero
			  else if(qta == null) throw new Exception("Tutti i Barcode devono avere una quantità");
			  else if(lunghezza == null) throw new Exception("Tutti i Barcode devono avere una lunghezza");
			  else if(qta <= 0) throw new Exception("Tutti i Barcode devono avere una quantità maggiore di zero");
			  else if(lunghezza <= 0) throw new Exception("Tutti i Barcode devono avere una lunghezza maggiore di zero");

			  if(nome == null || nome.isEmpty()){
				  nome = "barcode" + (count+1);
				  model.setValueAt(nome, count, 0);
			  }

			  
			  for(Barcode b : barcodes){
				 if(b.getNome().equals(nome)) throw new Exception("Due Barcode non possono avere lo stesso nome"); 
			  }
	  
			  barcodes.add(new Barcode(nome,lunghezza,qta));
		  }
		return barcodes;
	}

}
