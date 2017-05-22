package potter.linq;

import java.util.Iterator;

public interface IEnumerator<T> extends Iterator<T> {
	T getCurrent();

	boolean moveNext();
}
