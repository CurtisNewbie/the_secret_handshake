import java.util.*;


/**
 * @author yongj.zhuang
 */
public class ListHandshake {

    public static void main(String[] args) {
//        final List<String> l = new MyArrayList<>();
        final List<String> l = new MyLinkedList<>();
        l.add("1");
        l.add("2");
        l.add("3");
        l.add("3");
        l.add("3");
        System.out.println(l);
        System.out.printf("last index of 3, %d \n", l.lastIndexOf("3"));
        System.out.printf("last index of 2, %d \n", l.lastIndexOf("2"));
        System.out.printf("index of 2, %d \n", l.indexOf("2"));
        System.out.printf("index of 4, %d \n", l.indexOf("4"));
        System.out.printf("remove 2, %s \n", l.remove("2"));
        System.out.println(l);
        System.out.printf("remove 3, %s \n", l.remove("3"));
        System.out.println(l);
        l.add("4");
        System.out.println(l);
        System.out.printf("set index 2 to 5, %s \n", l.set(2, "5"));
        System.out.println(l);
        l.add("3");
        l.add("3");
        l.add("3");
        l.add("3");
        System.out.println(l);
        l.retainAll(Arrays.asList("1", "5", "4"));
        System.out.println(l);
        l.clear();
        System.out.println("l cleared");
        System.out.println(l);
        for (int i = 0; i < 50; i++) {
            l.add("" + i);
            System.out.println(l);
//            System.out.printf("%s\n", l.toShortStr());
        }
    }

}

/**
 * @param <T>
 * @author yongj.zhuang
 */
class MyArrayList<T> implements List<T> {
    Object[] arr;
    int size;

    public MyArrayList() {
        this(10);
    }

    public MyArrayList(int initCap) {
        this.arr = new Object[initCap];
        this.size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size < 1;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < arr.length; i++) {
            if (Objects.equals(arr[i], o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyArrayListIter<>(this, 0);
    }

    @Override
    public Object[] toArray() {
        Object[] copy = new Object[arr.length];
        System.arraycopy(arr, 0, copy, 0, arr.length);
        return copy;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        // TODO yongj.zhuang: not doing this
        return null;
    }

    @Override
    public boolean add(T t) {
        if (size == arr.length) {
            expand();
            return add(t);
        }

        arr[size++] = t;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(o, arr[i])) {
                removeAt(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T t : c) {
            add(t);
        }
        return true; // always true
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        for (T t : c) {
            add(index, t);
        }
        return true; // always true
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean removed = false;
        for (Object t : c) {
            if (remove(t)) {
                removed = true;
            }
        }
        return removed;

    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        final Object[] copy = toArray();
        for (Object o : copy) {
            if (!c.contains(o)) {
                remove(o);
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public void clear() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = null;
        }
        size = 0;
    }

    @Override
    public T get(int index) {
        ListHelper.checkBound(index, size);
        return (T) arr[index];
    }

    @Override
    public T set(int index, T element) {
        ListHelper.checkBound(index, size);
        T t = (T) arr[index];
        arr[index] = element;
        return t;
    }

    private void expand() {
        int frm = arr.length;
        int newLen = arr.length << 1;
        if (newLen < 0) {
            throw new OutOfMemoryError(
                    String.format("Array length too large, cannot continue to grow, newLength: %d", newLen));
        }
        Object[] expanded = new Object[newLen];
        System.arraycopy(arr, 0, expanded, 0, arr.length);
        arr = expanded;
        System.out.printf("%s grows from %d to %d\n", this.getClass().getSimpleName(), frm, arr.length);
    }

    @Override
    public void add(int j, T element) {
        ListHelper.checkBound(j, size);

        if (size == arr.length) {
            expand();
            add(j, element);
            return;
        }

        System.arraycopy(arr, j, arr, j + 1, size - j);
        arr[j] = element;
        ++size;
    }

    @Override
    public T remove(int j) {
        return removeAt(j);
    }

    private T removeAt(int j) {
        ListHelper.checkBound(j, size);
        T t = (T) arr[j];
        System.arraycopy(arr, j + 1, arr, j, arr.length - j - 1);
        arr[--size] = null;
        return t;
    }


    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < arr.length; i++) {
            if (Objects.equals(arr[i], o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = arr.length - 1; i > -1; i--) {
            if (Objects.equals(arr[i], o)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public String toString() {
        return String.format("MyArrayList{ size: %d, cap: %d, arr: %s }", size, arr.length, Arrays.toString(arr));
    }

    public String toShortStr() {
        return String.format("MyArrayList{ size: %d, cap: %d }", size, arr.length);
    }
}

/**
 * @param <T>
 * @author yongj.zhuang
 */
class MyArrayListIter<T> implements Iterator<T> {

    int i;
    MyArrayList<T> l;

    public MyArrayListIter(MyArrayList<T> l, int i) {
        this.l = l;
        this.i = i;
    }

    @Override
    public boolean hasNext() {
        return i < l.size() - 1;
    }

    @Override
    public T next() {
        return l.get(++i);
    }
}

class LNode<T> {
    LNode<T> prev;
    LNode<T> next;
    T t;

    public LNode(T t) {
        this.t = t;
    }
}

class MyLinkedList<T> implements List<T> {

    LNode<T> head;
    LNode<T> tail;
    int size;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size < 1;
    }

    @Override
    public boolean contains(Object o) {
        if (isEmpty()) return false;
        LNode<T> curr = head;
        while (curr != null) {
            if (Objects.equals(curr.t, o)) {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0]; // TODO yongj.zhuang: too lazy to impl
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null; // TODO yongj.zhuang: too lazy to impl
    }

    @Override
    public boolean add(T t) {
        final LNode<T> tn = new LNode<>(t);
        if (tail == null) {
            tail = tn;
            head = tn;
            ++size;
            return true;
        }

        tn.prev = tail;
        tail.next = tn;
        tail = tn;
        ++size;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        LNode<T> curr = head;
        while (curr != null) {
            if (Objects.equals(curr.t, o)) { // null remove is not supported
                curr.prev.next = curr.next;
                curr.next.prev = curr.prev;
                --size;
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T t : c) {
            add(t);
        }
        return true; // always true
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        for (T t : c) {
            add(t);
        }
        return true; // always true
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean removed = false;
        for (Object t : c) {
            if (remove(t)) {
                removed = true;
            }
        }
        return removed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        final Object[] copy = toArray();
        for (Object o : copy) {
            if (!c.contains(o)) {
                remove(o);
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public void clear() {
        LNode<T> curr = head;
        while (curr != null) {
            LNode<T> next = curr.next;
            curr.t = null;
            curr.prev = null;
            curr.next = null;
            curr = next;
        }
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public T get(int index) {
        ListHelper.checkBound(index, size);

        var curr = head;
        var i = 0;
        while (curr != null) {
            if (i == index) {
                return curr.t;
            }
            curr = curr.next;
            i++;
        }

        return null;
    }

    @Override
    public T set(int index, T element) {
        ListHelper.checkBound(index, size);

        var curr = head;
        var i = 0;
        while (curr != null) {
            if (i == index) {
                T t = curr.t;
                curr.t = element;
                return t;
            }
            curr = curr.next;
            i++;
        }
        return null;
    }


    @Override
    public void add(int index, T element) {
        if (index == size) {
            add(element);
            return;
        }
        ListHelper.checkBound(index, size);

        var curr = nodeAt(index);
        Objects.requireNonNull(curr);
        var ln = new LNode<>(element);
        ln.prev = curr.prev;
        ln.next = curr;

        if (curr.prev != null) {
            curr.prev.next = ln;
        } else {
            head = ln;
        }
        curr.prev = ln;
        size++;
    }

    @Override
    public T remove(int index) {
        ListHelper.checkBound(index, size);
        var curr = nodeAt(index);
        if (curr == null) {
            return null;
        }
        var p = curr.prev;
        var n = curr.next;
        if (p != null) {
            p.next = n;
        } else {
            head = n;
        }
        if (n != null) {
            n.prev = p;
        } else {
            tail = p;
        }
        curr.prev = null;
        curr.next = null;
        size--;
        return curr.t;
    }

    @Override
    public int indexOf(Object o) {
        var curr = head;
        var i = 0;
        while (curr != null) {
            if (Objects.equals(curr.t, o)) {
                return i;
            }
            curr = curr.next;
            i++;
        }
        return -1;
    }

    private LNode<T> nodeAt(final int j) {
        var curr = head;
        var i = 0;
        while (curr != null) {
            if (i == j) {
                return curr;
            }
            curr = curr.next;
            i++;
        }
        return null;
    }

    @Override
    public int lastIndexOf(Object o) {
        var curr = tail;
        var i = size - 1;
        while (curr != null) {
            if (Objects.equals(curr.t, o)) {
                return i;
            }
            i--;
            curr = curr.prev;
        }
        return -1;
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        sb.append("size: ").append(size).append(": ");
        var curr = head;
        while (curr != null) {
            sb.append(curr.t).append(" -> ");
            curr = curr.next;
        }
        return sb.toString();
    }
}

class ListHelper {

    public static void checkBound(int j, int size) {
        if (j < 0 || j >= size) {
            throw new IndexOutOfBoundsException(String.format("invalid index: %d, out of boundary, size: %d", j, size));
        }
    }
}
