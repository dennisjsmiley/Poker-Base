package poker.base.util;

import lombok.ToString;

@ToString
public class Tuple<S, T> {
    private final S x;
    private final T y;

    public Tuple(S x, T y) {
        this.x = x;
        this.y = y;
    }

    public S getX() {
        return x;
    }

    public T getY() {
        return y;
    }
}
