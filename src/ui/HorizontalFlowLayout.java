package ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.ArrayList;

public class HorizontalFlowLayout implements LayoutManager2
{
    final private ArrayList<Component> components = new ArrayList<Component>();
    private int hgap = 0;
    private int vgap = 0;
    private int hPad = 0;
    private int vPad = 0;

    public void setHGap(int hgap) { this.hgap = hgap; }
    public void setVGap(int vgap) { this.vgap = vgap; }
    public void setHPadding(int hPad) { this.hPad = hPad; }
    public void setVPadding(int vPad) { this.vPad = vPad; }

    @Override public void addLayoutComponent(Component comp, Object constraints) {
        this.components.add(comp);
    }

    /* these 3 methods need to be overridden properly */
    @Override public float getLayoutAlignmentX(Container target) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override public float getLayoutAlignmentY(Container target) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override public void invalidateLayout(Container target) {
        // TODO Auto-generated method stub
    }

    @Override public void addLayoutComponent(String name, Component comp) {
        this.components.add(comp);
    }

    @Override public void layoutContainer(Container parent) {
        int x = this.hPad;
        int y = this.vPad;
        int columnHeight = 0;
        for (Component c : this.components)
        {
            if (c.isVisible())
            {
                Dimension d = c.getPreferredSize();
                if (x+d.width > (parent.getWidth() - this.hPad))
                {
                    y += columnHeight + this.vgap;
                    x = this.hPad;
                }
                
                columnHeight = Math.max(columnHeight, d.height);
                c.setBounds(x, y, d.width, d.height);
                x += d.width + this.hgap;              
            }
        }       
    }

    /* these 3 methods need to be overridden properly */
    @Override public Dimension minimumLayoutSize(Container parent) {
        return new Dimension(0,0);
    }

    @Override public Dimension preferredLayoutSize(Container parent) {
        return new Dimension(200,200);
    }

    @Override public Dimension maximumLayoutSize(Container target) {
        return new Dimension(32767, 32767);
    }


    @Override public void removeLayoutComponent(Component comp) {
        this.components.remove(comp);
    }
}