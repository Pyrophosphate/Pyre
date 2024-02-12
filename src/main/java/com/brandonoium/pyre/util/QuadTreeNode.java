package com.brandonoium.pyre.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class QuadTreeNode<T> {
    private QuadTreeNode<T> nw, ne, sw, se;
    private boolean isLeaf = true;
    private int size;
    private int x, y, cx, cy;

    private List<Entry<Location, T>> data;


    public QuadTreeNode(int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.cx = x + (size/2);
        this.cy = y + (size/2);

        data = new ArrayList<>();
    }

    public List<Entry<Location, T>> getData() {
        return data;
    }

    public void addDataAtLocation(int x, int y, T data) {
        if(isLeaf) {
            this.data.add(Map.entry(new Location(x, y), data));
        } else if(x > cx) {
            if(y > cy) {
                se.addDataAtLocation(x, y, data);
            } else {
                ne.addDataAtLocation(x, y, data);
            }
        } else {
            if(y > cy) {
                sw.addDataAtLocation(x, y, data);
            } else {
                nw.addDataAtLocation(x, y, data);
            }
        }
    }

    public void removeDataAtLocation(int x, int y, T data) {
        if(isLeaf) {
            this.data.removeIf(d -> d.getValue() == data
                    && d.getKey().getX() == x
                    && d.getKey().getY() == y);
        } else if(x > cx) {
            if(y > cy) {
                se.removeDataAtLocation(x, y, data);
            } else {
                ne.removeDataAtLocation(x, y, data);
            }
        } else {
            if(y > cy) {
                sw.removeDataAtLocation(x, y, data);
            } else {
                nw.removeDataAtLocation(x, y, data);
            }
        }
    }

    public List<T> getDataAtLocation(int x, int y) {
        if(isLeaf) {
            List<T> returnList = new ArrayList<>();
            for(Entry<Location, T> d : data) {
                if(d.getKey().getX() == x && d.getKey().getY() == y) {
                    returnList.add(d.getValue());
                }
            }
            return returnList;
        } else if(x > cx) {
            if(y > cy) {
                return se.getDataAtLocation(x, y);
            } else {
                return ne.getDataAtLocation(x, y);
            }
        } else {
            if(y > cy) {
                return sw.getDataAtLocation(x, y);
            } else {
                return nw.getDataAtLocation(x, y);
            }
        }
    }

    public void growChildren() {
        nw = new QuadTreeNode<T>(x, y, size/2);
        ne = new QuadTreeNode<T>(cx, y, size/2);
        sw = new QuadTreeNode<T>(x, cy, size/2);
        se = new QuadTreeNode<T>(cx, cy, size/2);

        for(Entry<Location, T> d : data) {
            this.addDataAtLocation(d.getKey().getX(), d.getKey().getY(), d.getValue());
        }

        data = null;
        isLeaf = false;
    }

    protected void removeChildren() {
        if(!isLeaf) {
            isLeaf = true;
            data = new ArrayList<>();

            data.addAll(nw.getData());
            data.addAll(ne.getData());
            data.addAll(sw.getData());
            data.addAll(se.getData());

            nw = null;
            ne = null;
            sw = null;
            se = null;
        }
    }


    protected void growTreeToMaximumCount(int maxCount) {
        if(isLeaf) {
            if (data.size() > maxCount) {
                growChildren();
            }
        } else {
            nw.growTreeToMaximumCount(maxCount);
            ne.growTreeToMaximumCount(maxCount);
            sw.growTreeToMaximumCount(maxCount);
            se.growTreeToMaximumCount(maxCount);
        }
    }

    protected int trimTreeToMaximumCount(int maxCount) {
        int sum = 0;
        sum += nw.trimTreeToMaximumCount(maxCount);
        sum += ne.trimTreeToMaximumCount(maxCount);
        sum += sw.trimTreeToMaximumCount(maxCount);
        sum += se.trimTreeToMaximumCount(maxCount);

        if(sum <= maxCount) {
            removeChildren();
        }

        return sum;
    }


    public QuadTreeNode<T> getNw() {
        return nw;
    }

    public void setNw(QuadTreeNode<T> nw) {
        this.nw = nw;
    }

    public QuadTreeNode<T> getNe() {
        return ne;
    }

    public void setNe(QuadTreeNode<T> ne) {
        this.ne = ne;
    }

    public QuadTreeNode<T> getSw() {
        return sw;
    }

    public void setSw(QuadTreeNode<T> sw) {
        this.sw = sw;
    }

    public QuadTreeNode<T> getSe() {
        return se;
    }

    public void setSe(QuadTreeNode<T> se) {
        this.se = se;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
