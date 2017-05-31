package potter.linq.tests;

public class Tuple2<T1, T2>
{
    public Tuple2(T1 item1, T2 item2)
    {
        this.item1 = item1;
        this.item2 = item2;
    }

    private T1 item1;
    private T2 item2;

    public T1 getItem1()
    {
        return item1;
    }

    public void setItem1(T1 item1)
    {
        this.item1 = item1;
    }

    public T2 getItem2()
    {
        return item2;
    }

    public void setItem2(T2 item2)
    {
        this.item2 = item2;
    }

    @Override
    public String toString()
    {
        return "Tuple2 [item1=" + item1 + ", item2=" + item2 + "]";
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((item1 == null) ? 0 : item1.hashCode());
        result = prime * result + ((item2 == null) ? 0 : item2.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }

        if (obj instanceof Tuple2 == false)
        {
            return false;
        }

        @SuppressWarnings("rawtypes")
        Tuple2 other = (Tuple2) obj;

        if (item1 == null)
        {
            if (other.item1 != null)
            {
                return false;
            }
        }
        else if (item1.equals(other.item1) == false)
        {
            return false;
        }

        if (item2 == null)
        {
            if (other.item2 != null)
            {
                return false;
            }
        }
        else if (item2.equals(other.item2) == false)
        {
            return false;
        }

        return true;
    }
}
