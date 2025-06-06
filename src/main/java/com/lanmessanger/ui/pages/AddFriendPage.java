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

import main.java.com.lanmessanger.ui.components.ModernScrollBarUI;
import main.java.com.lanmessanger.ui.components.addFriendPage.BottomPanel;
import main.java.com.lanmessanger.ui.components.addFriendPage.CenterPanel;
import main.java.com.lanmessanger.ui.components.addFriendPage.HeadingPanel;
import main.java.com.lanmessanger.ui.utils.ColorPalette;

/** Add friend page which have function to add friend by giving IP Address
 * @author Shoyeb Ansari
 */
public class AddFriendPage extends JPanel {

    /** Top panel which contains heading */
    private HeadingPanel topPanel;
    /** Center panel which contains logo and the curent users' ip address and providing copy functionality */
    private CenterPanel centerPanel;
    /** Bottom panel where we can add user */
    private BottomPanel bottomPanel;
    /** To provide the functionality of scroll bar when the dimensions are low */
    private JScrollPane scrollPane;
    private ScrollablePanel contentPanel;

    public AddFriendPage() {
        try {
            // Main panel uses BorderLayout
            setLayout(new BorderLayout());
            setBackground(ColorPalette.BACKGROUND);

            // Create custom scrollable content panel that will hold all components
            contentPanel = new ScrollablePanel();
            contentPanel.setBackground(ColorPalette.BACKGROUND);
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

            // Initialize components with proper constraints
            topPanel = new HeadingPanel("Add friend to start chatting");
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

            // Style the scrollbar
            scrollPane.getVerticalScrollBar().setBackground(ColorPalette.BACKGROUND);
            scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());

            // Add scrollable panel to main panel with margins
            JPanel wrapperPanel = new JPanel(new BorderLayout());
            wrapperPanel.setBackground(ColorPalette.BACKGROUND);
            wrapperPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            wrapperPanel.add(scrollPane, BorderLayout.CENTER);

            add(wrapperPanel, BorderLayout.CENTER);

            // Add a component listener to handle resize events properly
            addComponentListener(new java.awt.event.ComponentAdapter() {
                public void componentResized(java.awt.event.ComponentEvent evt) {
                    try {
                        // Ensure layout updates properly after resize
                        SwingUtilities.invokeLater(() -> {
                            contentPanel.revalidate();
                            scrollPane.revalidate();
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            try {
                return getPreferredSize();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        
        @Override
        public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
            try {
                return 16;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
        
        @Override
        public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
            try {
                return 100;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
        }
        
        @Override
        public boolean getScrollableTracksViewportWidth() {
            try {
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        
        @Override
        public boolean getScrollableTracksViewportHeight() {
            try {
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}