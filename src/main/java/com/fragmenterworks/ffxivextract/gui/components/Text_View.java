package com.fragmenterworks.ffxivextract.gui.components;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@SuppressWarnings("serial")
public class Text_View extends JScrollPane {

    private final JTable luaCodeTable;
    private final JTextArea textArea;
    private final JScrollPane scrollPane;
    private String[] codeLines = new String[0];
    private Charset charset = StandardCharsets.UTF_8;

    public Text_View() {
        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);
        textArea.setFont(new Font("MS Gothic", Font.PLAIN, 14));
        textArea.setLineWrap(false);
        textArea.setWrapStyleWord(false);

        scrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        getViewport().add(textArea);

        luaCodeTable = new JTable(new Text_View.LuaCodeTableModel());

        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL
                | GridBagConstraints.VERTICAL;
        //getViewport().add(luaCodeTable);
        luaCodeTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        TableColumn tableColumn = luaCodeTable.getColumnModel().getColumn(0);
        int preferredWidth = tableColumn.getMinWidth();
        int maxWidth = tableColumn.getMaxWidth();

        for (int row = 0; row < luaCodeTable.getRowCount(); row++) {
            TableCellRenderer cellRenderer = luaCodeTable.getCellRenderer(row, 0);
            Component c = luaCodeTable.prepareRenderer(cellRenderer, row, 0);
            int width = c.getPreferredSize().width + luaCodeTable.getIntercellSpacing().width;
            preferredWidth = Math.max(preferredWidth, width);

            //  We've exceeded the maximum width, no need to check other rows

            if (preferredWidth >= maxWidth) {
                preferredWidth = maxWidth;
                break;
            }
        }

        tableColumn.setMinWidth(preferredWidth);
        tableColumn.setMaxWidth(preferredWidth);

        //Graphics Stuff
        DefaultTableCellRenderer cellRender = new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table,
                                                           Object value, boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                JTableHeader header = table.getTableHeader();
                if (header != null) {
                    setFont(header.getFont());
                }

                if (column == 0)
                    setHorizontalAlignment(JLabel.RIGHT);
                else
                    setHorizontalAlignment(JLabel.LEFT);

                setText((value == null) ? "" : value.toString());


                if (column == 0) {
                    setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2,
                            Color.LIGHT_GRAY));
                    setForeground(Color.LIGHT_GRAY);
                } else {
                    setForeground(Color.BLACK);
                    setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
                }

                return this;
            }
        };
        luaCodeTable.setTableHeader(null);
        luaCodeTable.setShowGrid(false);
        luaCodeTable.setIntercellSpacing(new Dimension(0, 0));
        luaCodeTable.setDefaultRenderer(Object.class, cellRender);
    }

    public void setBytes(byte[] data) {
        if (data == null)
        {
            codeLines = null;
            return;
        }

        textArea.setText(new String(data, charset));
    }

    @SuppressWarnings("serial")
    class LuaCodeTableModel extends AbstractTableModel {

        @Override
        public int getColumnCount() {
            return 2;
        }

        @Override
        public int getRowCount() {
            if (codeLines == null)
                return 0;
            else
                return codeLines.length;
        }

        @Override
        public String getColumnName(int column) {
            return "";
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == 0)
                return " " + (rowIndex + 1) + " ";
            else
                return codeLines[rowIndex];
        }

    }

}
