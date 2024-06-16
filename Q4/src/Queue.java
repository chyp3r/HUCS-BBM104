import java.util.ArrayList;

class Queue<T> {
    private ArrayList<T> queuList;

    public Queue() {
        this.queuList = new ArrayList<T>();
    }

    public void addCommandToQueue(T item) {
        this.queuList.add(0,item);
    }

    public void removeCommandFromQueue() {
        if (!this.isQueueEmpty())
            this.queuList.remove(queuList.size()-1);

    }

    public T getFrontofCommandQueue() {
        return this.queuList.get(queuList.size()-1);
    }

    public boolean isQueueEmpty() {
        return queuList.isEmpty();
    }

}
