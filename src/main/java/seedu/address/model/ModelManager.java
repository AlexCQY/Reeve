package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.student.Student;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Reeve reeve;
    private final UserPrefs userPrefs;
    private final FilteredList<Student> filteredStudents;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyReeve addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.reeve = new Reeve(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredStudents = new FilteredList<>(this.reeve.getStudentList());
    }

    public ModelManager() {
        this(new Reeve(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setReeve(ReadOnlyReeve reeve) {
        this.reeve.resetData(reeve);
    }

    @Override
    public ReadOnlyReeve getReeve() {
        return reeve;
    }

    @Override
    public boolean hasPerson(Student student) {
        requireNonNull(student);
        return reeve.hasStudent(student);
    }

    @Override
    public void deletePerson(Student target) {
        reeve.removeStudent(target);
    }

    @Override
    public void addPerson(Student student) {
        reeve.addStudent(student);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Student target, Student editedStudent) {
        requireAllNonNull(target, editedStudent);

        reeve.setStudent(target, editedStudent);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Student> getFilteredPersonList() {
        return filteredStudents;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Student> predicate) {
        requireNonNull(predicate);
        filteredStudents.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return reeve.equals(other.reeve)
                && userPrefs.equals(other.userPrefs)
                && filteredStudents.equals(other.filteredStudents);
    }

    /**
     * Makes a copy of the student list in reeve, sorts the copied list and replace the current student list with the
     * sorted list
     * @param comparator for the student objects, can be comparing based on fields that student has
     */
    @Override
    public void sortStudentList(Comparator<? super Student> comparator) {
        requireNonNull(comparator);
        List<Student> lst = new ArrayList<>(reeve.getStudentList());
        lst.sort(comparator);
        reeve.setStudents(lst);
    }

}
