package main.java.com.lanmessanger.ui.pages;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import main.java.com.lanmessanger.ui.components.addFriendPage.BottomPanel;
import main.java.com.lanmessanger.ui.components.addFriendPage.CenterPanel;
import main.java.com.lanmessanger.ui.components.addFriendPage.TopPanel;
import main.java.com.lanmessanger.ui.utils.ColorPalette;

public class AddFriendPage extends JPanel {

    private TopPanel topPanel;
    private CenterPanel centerPanel;
    private BottomPanel bottomPanel;
    private JScrollPane scrollPane;
    private ScrollablePanel contentPanel;

    public AddFriendPage() {
        // Main panel uses BorderLayout
        setLayout(new BorderLayout());
        setBackground(ColorPalette.BACKGROUND);
        
        // Create custom scrollable content panel that will hold all components
        contentPanel = new ScrollablePanel();
        contentPanel.setBackground(ColorPalette.BACKGROUND);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        // Initialize components with proper constraints
        topPanel = new TopPanel();
        topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        
        centerPanel = new CenterPanel();
        centerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        
        bottomPanel = new BottomPanel();
        centerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));
        
        // Add components
        contentPanel.add(topPanel);
        contentPanel.add(centerPanel);
        contentPanel.add(bottomPanel);
        
        // Create scroll pane - Using AS_NEEDED for vertical scrollbar
        scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smoother scrolling
        scrollPane.setBackground(ColorPalette.BACKGROUND);
        scrollPane.getViewport().setBackground(ColorPalette.BACKGROUND);
        
        // Ensure scrollbar doesn't cause layout thrashing when appearing/disappearing
        scrollPane.getVerticalScrollBar().setPreferredSize(
            new Dimension(scrollPane.getVerticalScrollBar().getPreferredSize().width, 0));
        
        // Add scrollable panel to main panel with margins
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(ColorPalette.BACKGROUND);
        wrapperPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        wrapperPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(wrapperPanel, BorderLayout.CENTER);
        
        // Add a component listener to handle resize events properly
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                // Ensure layout updates properly after resize
                SwingUtilities.invokeLater(() -> {
                    contentPanel.revalidate();
                    scrollPane.revalidate();
                });
            }
        });
    }
    
    /**
     * Custom scrollable panel that properly handles tracking width changes
     * while allowing vertical scrolling
     */
    private class ScrollablePanel extends JPanel implements Scrollable {
        
        public ScrollablePanel() {
            super();
        }
        
        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return getPreferredSize();
        }
        
        @Override
        public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 16;
        }
        
        @Override
        public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
            return 100;
        }
        
        // This is key - tells scroll pane to match width but allow vertical scrolling
        // @Override'
        public boolean getScrollableTracksViewportWidth() {
            return true;
        }
        
        // Allow vertical scrolling
        @Override
        public boolean getScrollableTracksViewportHeight() {
            return false;
        }
    }
}