package app.ui.gui;

import javafx.stage.FileChooser;

public class FileChooserCsvFiles {
    private FileChooser fileChooser;

    private FileChooserCsvFiles() {
        fileChooser = new FileChooser();
        associarFiltro("CSV files", "*.csv");
    }

    public static FileChooser createCsvFileChooser() {
        FileChooserCsvFiles fcListaTelefonica = new FileChooserCsvFiles();
        return fcListaTelefonica.fileChooser;
    }

    private void associarFiltro(String descricao, String extensao) {
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter(descricao, extensao);
        fileChooser.getExtensionFilters().add(filter);
    }
}
