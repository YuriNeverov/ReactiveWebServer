package utils;

import java.util.function.Function;

public class Either<L, R> {
    private final L left;
    private final R right;

    private Either(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public <L1> Either<L1, R> bind(Function<L, Either<L1, R>> f) {
        if (left != null) {
            return f.apply(left);
        }
        return right(right);
    }

    public <T> T match(Function<L, T> leftFun, Function<R, T> rightFun) {
        if (left != null) {
            return leftFun.apply(left);
        }
        return rightFun.apply(right);
    }

    public static <L, R> Either<L, R> left(L left) {
        return new Either<>(left, null);
    }

    public static <L, R> Either<L, R> right(R right) {
        return new Either<>(null, right);
    }
}
