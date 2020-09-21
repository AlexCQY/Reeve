package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

public class Remark {
    public final String remark;

    public Remark(String remark) {
        requireNonNull(remark);
        this.remark = remark;
    }

    @Override
    public String toString() {
        return this.remark;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (obj instanceof Remark) {
            Remark rmk = (Remark) obj;
            return this.remark.equals(rmk.remark);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return remark.hashCode();
    }
}
