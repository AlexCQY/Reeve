package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

public class Remark {
    public static final String INVALID_REMARK_MESSAGE = "Invalid remark";
    public final String value;

    /**
     * Constructor for a Remark
     * * @param value the remark string
     */
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
