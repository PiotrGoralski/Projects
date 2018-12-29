package Table;


import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModel extends AbstractTableModel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] columns;
    private List<Object[]> datas;

    public TableModel(List<Object[]> datas, String[] columns) {
        this.datas = datas;
        this.columns = columns;
    }

    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 3:
                return JLabel.class;
            case 2:
                return Integer.class;
        }
        return String.class;
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public int getRowCount() {
        return datas.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return datas.get(rowIndex)[columnIndex];
    }

    public void addRow(Object[] dat) {
        datas.add(dat);
        int row = datas.indexOf(dat);
        for (int column = 0; column < dat.length; column++) {
            fireTableCellUpdated(row, column);
        }
        fireTableRowsInserted(row, row);
    }

    public void removeRow(int rowIndex) {
        datas.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public String getColumnName(int col) {
        return columns[col];
    }

    public void setValueAt(Object val, int rowIndex, int columnIndex) {
        datas.get(rowIndex)[columnIndex] = val;
        fireTableCellUpdated(rowIndex, columnIndex);
    }
}