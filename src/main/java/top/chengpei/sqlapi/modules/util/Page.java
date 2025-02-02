package top.chengpei.sqlapi.modules.util;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    protected List<T> records;
    protected long total;
    protected long size;
    protected long current;

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }
}
