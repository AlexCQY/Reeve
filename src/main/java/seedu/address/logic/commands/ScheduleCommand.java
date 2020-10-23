package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.student.Student;

/**
 * Finds and list all students with classes on a given date.
 * This is also the schedule of the user on that particular date.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Given a date in the format of dd/mm/yy "
            + "outputs all classes together with the respective student on the date"
            + "and displays them as a list with index numbers.\n"
            + "Parameters: DATE \n"
            + "Example: " + COMMAND_WORD + " 27/10/20";

    public static final String INCORRECT_DATE_FORMAT = "Date should be in the format dd/mm/yyyy";
    public static final String EMPTY_DATE_MESSAGE = "Please input a date";

    private final LocalDate dateToFindSchedule;

    public ScheduleCommand(LocalDate dateTime) {
        this.dateToFindSchedule = dateTime;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        // this date is given by the user, we extract out the day
        DayOfWeek day = this.dateToFindSchedule.getDayOfWeek();

        assert day != null;

        // checks which student has the same day as the one given extracted out
        Predicate<Student> predicate = student -> student.getAdmin().getClassTime().isSameDay(day);

        // updates the list that is currently showed in the ui
        model.updateFilteredPersonList(predicate);

        // Comparator to sort student based on classTime
        Comparator<Student> cmp = (Comparator.comparing(o -> o.getAdmin().getClassTime()));
        model.sortStudentList(cmp);

        return new CommandResult(
                String.format(Messages.MESSAGE_STUDENTS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleCommand // instanceof handles nulls
                && dateToFindSchedule.equals(((ScheduleCommand) other).dateToFindSchedule)); // state check
    }
}
