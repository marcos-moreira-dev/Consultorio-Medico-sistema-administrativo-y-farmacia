package dev.marcosmoreira.desktopconsultorio.components.shared.calendar;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.function.Consumer;

/**
 * Calendario mensual interactivo construido con GridPane.
 *
 * <p>Muestra los días del mes en una grilla 7×6 con navegación entre meses.
 * Cada celda es un botón que dispara un callback al seleccionar un día.
 * Los días con citas se resaltan visualmente.</p>
 *
 * <p>Clases CSS: {@code .cal-day}, {@code .cal-day:today}, {@code .cal-day:selected},
 * {@code .cal-day:has-citas}, {@code .cal-day:disabled}</p>
 *
 * @author Marcos Moreira
 * @since 1.0
 */
public class CalendarGrid extends GridPane {

    private YearMonth currentMonth;
    private LocalDate selectedDate;
    private final Label monthLabel = new Label();
    private final Button prevBtn = new Button("◀");
    private final Button nextBtn = new Button("▶");

    private Consumer<LocalDate> onDateSelected;
    private java.util.Set<LocalDate> datesWithEvents = java.util.Collections.emptySet();

    public CalendarGrid() {
        this(YearMonth.now());
    }

    public CalendarGrid(YearMonth initialMonth) {
        this.currentMonth = initialMonth;
        this.selectedDate = LocalDate.now();

        prevBtn.getStyleClass().add("btn-ghost");
        prevBtn.setOnAction(e -> navigateMonth(-1));

        nextBtn.getStyleClass().add("btn-ghost");
        nextBtn.setOnAction(e -> navigateMonth(1));

        HBox header = new HBox(16, prevBtn, monthLabel, nextBtn);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(8));
        monthLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        add(header, 0, 0, 7, 1);

        // Day headers
        String[] days = {"Lu", "Ma", "Mi", "Ju", "Vi", "Sa", "Do"};
        for (int i = 0; i < 7; i++) {
            Label dayLabel = new Label(days[i]);
            dayLabel.setAlignment(Pos.CENTER);
            dayLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #6b7f8e;");
            dayLabel.setMinHeight(34);
            dayLabel.setAlignment(Pos.CENTER);
            add(dayLabel, i, 1);
        }

        renderCalendar();
    }

    private void renderCalendar() {
        monthLabel.setText(currentMonth.getMonth().getDisplayName(TextStyle.FULL, new Locale("es", "ES"))
                + " " + currentMonth.getYear());

        // Clear previous days (rows 2-7)
        getChildren().removeIf(node -> GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) > 1);

        LocalDate firstDay = currentMonth.atDay(1);
        int startCol = (firstDay.getDayOfWeek().getValue() + 6) % 7; // Monday = 0

        LocalDate today = LocalDate.now();
        int row = 2;
        int col = startCol;

        for (int day = 1; day <= currentMonth.lengthOfMonth(); day++) {
            LocalDate date = currentMonth.atDay(day);
            Button dayBtn = createDayButton(date, today);
            add(dayBtn, col, row);

            col++;
            if (col > 6) {
                col = 0;
                row++;
            }
        }
    }

    private Button createDayButton(LocalDate date, LocalDate today) {
        Button btn = new Button(String.valueOf(date.getDayOfMonth()));
        btn.getStyleClass().add("cal-day");
        btn.setMinSize(56, 48);
        btn.setPrefSize(72, 56);
        btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        if (date.equals(today)) {
            btn.getStyleClass().add("today");
        }

        if (date.equals(selectedDate)) {
            btn.getStyleClass().add("selected");
        }

        if (datesWithEvents.contains(date)) {
            btn.getStyleClass().add("has-citas");
        }

        if (date.isBefore(currentMonth.atDay(1)) || date.isAfter(currentMonth.atEndOfMonth())) {
            btn.getStyleClass().add("disabled");
        }

        btn.setOnAction(e -> {
            selectedDate = date;
            renderCalendar(); // Re-render to update selection
            if (onDateSelected != null) {
                onDateSelected.accept(date);
            }
        });

        return btn;
    }

    private void navigateMonth(int delta) {
        currentMonth = currentMonth.plusMonths(delta);
        renderCalendar();
    }

    /**
     * Callback cuando se selecciona un día.
     */
    public void onDateSelected(Consumer<LocalDate> handler) {
        this.onDateSelected = handler;
    }

    /**
     * Marca qué días tienen eventos (citas) para resaltarlos visualmente.
     */
    public void setDatesWithEvents(java.util.Set<LocalDate> dates) {
        this.datesWithEvents = dates != null ? dates : java.util.Collections.emptySet();
        renderCalendar();
    }

    /**
     * Selecciona un día específico y dispara el callback.
     */
    public void selectDate(LocalDate date) {
        if (date != null) {
            this.selectedDate = date;
            if (date.getYear() != currentMonth.getYear() || date.getMonth() != currentMonth.getMonth()) {
                currentMonth = YearMonth.from(date);
            }
            renderCalendar();
            if (onDateSelected != null) {
                onDateSelected.accept(date);
            }
        }
    }

    public LocalDate getSelectedDate() {
        return selectedDate;
    }

    public YearMonth getCurrentMonth() {
        return currentMonth;
    }
}
