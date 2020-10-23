package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalStudents.getTypicalAddressBook;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.Reeve;
import seedu.address.model.UserPrefs;
import seedu.address.model.student.Student;

public class ScheduleCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        LocalDate date1 = LocalDate.of(2020, 11, 3);
        LocalDate date2 = LocalDate.of(2020, 11, 4);

        ScheduleCommand scheduleCommand1 = new ScheduleCommand(date1);
        ScheduleCommand scheduleCommand2 = new ScheduleCommand(date2);

        // same object -> true
        assertTrue(scheduleCommand1.equals(scheduleCommand1));

        // different object -> false
        assertFalse(scheduleCommand1.equals(scheduleCommand2));

        // same date return true
        ScheduleCommand scheduleCommand1Copy = new ScheduleCommand(date1);
        assertTrue(scheduleCommand1.equals(scheduleCommand1Copy));

        // null return false
        assertFalse(scheduleCommand1.equals(null));
    }

    @Test
    public void execute_validDate_success() {
        LocalDate validDate = LocalDate.of(2020, 11, 3);
        DayOfWeek day = validDate.getDayOfWeek();

        Comparator<Student> comparator = Comparator.comparing(o -> o.getAdmin().getClassTime());

        Predicate<Student> classTimeIsSameDay = student -> student.getAdmin().getClassTime().isSameDay(day);

        Model copyModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        copyModel.updateFilteredPersonList(classTimeIsSameDay);
        List<Student> sortedStudentList =
                copyModel.getFilteredPersonList().stream().sorted(comparator).collect(Collectors.toList());
        ObservableList<Student> sortedStudentObservableList = FXCollections.observableArrayList(sortedStudentList);

        Model expectedModel = new ModelManager(new Reeve(model.getReeve()), new UserPrefs());
        expectedModel.updateFilteredPersonList(classTimeIsSameDay);
        expectedModel.sortStudentList(comparator);

        String expectedMsg = String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW,
                expectedModel.getFilteredPersonList().size());

        ScheduleCommand scheduleCommand = new ScheduleCommand(validDate);

        assertCommandSuccess(scheduleCommand, model, expectedMsg, expectedModel);

        assertEquals(expectedModel.getFilteredPersonList(), sortedStudentObservableList);

    }


    @Test
    public void execute_nullDateToFindSchedule_throwsNullPointerException() {
        ScheduleCommand scheduleCommand = new ScheduleCommand(null);
        assertThrows(NullPointerException.class, () -> scheduleCommand.execute(model));
    }


}
