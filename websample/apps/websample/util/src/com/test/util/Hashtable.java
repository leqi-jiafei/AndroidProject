package com.test.util;

import java.io.Serializable;
import java.util.Map;


/**
 * TODO description here
 *
 * @author Qiu Le Qi
 * @version $Revision: 1.1 $
  */
public class Hashtable extends java.util.Hashtable implements Serializable {
    /**
     * TODO description here
     */
    private static final long serialVersionUID = -2224831155364079963L;

    /** The hash table data. */
    private transient Entry[] table;

    /** The total number of entries in the hash table. */
    private transient int count;

    /**
     * The table is rehashed when its size exceeds this threshold.  (The value of this
     * field is (int)(capacity  loadFactor).)
     *
     * @serial
     */
    private int threshold;

    /**
     * The load factor for the hashtable.
     *
     * @serial
     */
    private float loadFactor;

/**
     * Constructs a new, empty hashtable with the specified initial
     * capacity and the specified load factor.
     *
     * @param      initialCapacity   the initial capacity of the hashtable.
     * @param      loadFactor        the load factor of the hashtable.
     * @exception  IllegalArgumentException  if the initial capacity is less
     *             than zero, or if the load factor is nonpositive.
     */
    public Hashtable(int initialCapacity, float loadFactor) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }

        if ((loadFactor <= 0) || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Illegal Load: " + loadFactor);
        }

        if (initialCapacity == 0) {
            initialCapacity = 1;
        }

        this.loadFactor     = loadFactor;
        table               = new Entry[initialCapacity];
        threshold           = (int) (initialCapacity * loadFactor);
    }

/**
     * Constructs a new, empty hashtable with the specified initial capacity
     * and default load factor, which is <tt>0.75</tt>.
     *
     * @param     initialCapacity   the initial capacity of the hashtable.
     * @exception IllegalArgumentException if the initial capacity is less
     *              than zero.
     */
    public Hashtable(int initialCapacity) {
        this(initialCapacity, 0.75f);
    }

/**
     * Constructs a new, empty hashtable with a default initial capacity (11)
     * and load factor, which is <tt>0.75</tt>.
     */
    public Hashtable() {
        this(11, 0.75f);
    }

    /**
     * TODO Auto-generated method
     *
     * @param key TODO description here
     * @param value TODO description here
     *
     * @return TODO description here
     */
    public Object put(Object key, Object value) {
        return super.put(key, ((value == null) ? new NullObject() : value));
    }

    /**
     * TODO Auto-generated method
     *
     * @param key TODO description here
     *
     * @return TODO description here
     */
    public Object get(Object key) {
        Object value = super.get(key);

        return ((value instanceof NullObject) ? null : value);
    }

    /**
     * TODO description here
     *
     * @author darkoj
     * @version $Revision: 1.1 $
      */
    class NullObject {
        // internal respresentation for null
        /**
         * TODO Auto-generated method
         *
         * @return TODO description here
         */
        public String toString() {
            return "NULL";
        }
    }

    /**
     * Hashtable collision list.
     */
    private static class Entry implements Map.Entry {
        /**
         * TODO description here
         */
        int hash;

        /**
         * TODO description here
         */
        Object key;

        /**
         * TODO description here
         */
        Object value;

        /**
         * TODO description here
         */
        Entry next;

        /**
         * Creates a new Entry object.
         *
         * @param hash TODO description here
         * @param key TODO description here
         * @param value TODO description here
         * @param next TODO description here
         */
        protected Entry(int hash, Object key, Object value, Entry next) {
            this.hash      = hash;
            this.key       = key;
            this.value     = value;
            this.next      = next;
        }

        /**
         * TODO Auto-generated method
         *
         * @return TODO description here
         */
        protected Object clone() {
            return new Entry(hash, key, value, ((next == null) ? null : (Entry) next.clone()));
        }

        // Map.Entry Ops
        /**
         * TODO Auto-generated method
         *
         * @return TODO description here
         */
        public Object getKey() {
            return key;
        }

        /**
         * TODO Auto-generated method
         *
         * @return TODO description here
         */
        public Object getValue() {
            return value;
        }

        /**
         * TODO Auto-generated method
         *
         * @param value TODO description here
         *
         * @return TODO description here
         */
        public Object setValue(Object value) {
            if (value == null) {
                throw new NullPointerException();
            }

            Object oldValue = this.value;
            this.value = value;

            return oldValue;
        }

        /**
         * TODO Auto-generated method
         *
         * @param o TODO description here
         *
         * @return TODO description here
         */
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }

            Map.Entry e = (Map.Entry) o;

            return ((key == null) ? (e.getKey() == null) : key.equals(e.getKey())) &&
                   ((value == null) ? (e.getValue() == null) : value.equals(e.getValue()));
        }

        /**
         * TODO Auto-generated method
         *
         * @return TODO description here
         */
        public int hashCode() {
            return hash ^ ((value == null) ? 0 : value.hashCode());
        }

        /**
         * TODO Auto-generated method
         *
         * @return TODO description here
         */
        public String toString() {
            return key.toString() + "=" + value.toString();
        }
    }
}
