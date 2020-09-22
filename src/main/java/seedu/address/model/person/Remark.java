package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

public class Remark {
    public final String value;
    public static final String INVALID_REMARK_MESSAGE = "Invalid remark";

    public Remark(String value) {
        requireNonNull(value);
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof Remark) {
            Remark rmk = (Remark) obj;
            return this.value.equals(rmk.value);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
