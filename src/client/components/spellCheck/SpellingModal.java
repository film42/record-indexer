package client.components.spellCheck;

import client.components.downloadModal.DownloadModal;
import client.modules.spellChecker.KnownData;
import client.modules.spellChecker.SpellChecker;
import client.modules.spellChecker.WordSelectedListener;
import client.persistence.Cell;
import client.persistence.ImageState;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created with IntelliJ IDEA.
 * User: film42
 * Date: 12/12/13
 * Time: 5:37 PM
 */
public class SpellingModal extends JDialog {

    private ImageState imageState;
    private int column;
    private String word;
    private JButton button;
    private JList<String> jList;
    private String[] words;
    private WordSelectedListener wordSelectedListener;

    public SpellingModal(ImageState imageState, String word, int column,
                         WordSelectedListener wordSelectedListener) {
        this.imageState = imageState;
        this.column = column;
        this.word = word;
        this.wordSelectedListener = wordSelectedListener;

        setupView();
    }

    private void setupView() {
        this.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
        this.setTitle("Spelling Suggestions");
        this.setSize(350, 500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        KnownData knownData = imageState.getKnownDataValues().get(column);

        jList = new JList<>();

        SpellChecker spellChecker = new SpellChecker();
        spellChecker.getSuggestionsForString(word);
        spellChecker.restrictToList(knownData.getWords());

        words = spellChecker.getWordArray();

        jList.setListData(words);
        jList.addListSelectionListener(listSelectionListener);

        this.add(new JScrollPane(jList), BorderLayout.CENTER);

        button = new JButton("Use Suggestion");
        button.setEnabled(false);
        button.addActionListener(actionListener);
        this.add(button, BorderLayout.SOUTH);
    }

    private ActionListener actionListener = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int index = jList.getSelectedIndex();

            String word = words[index];

            wordSelectedListener.wordSelected(word);

            dispatchEvent(new WindowEvent(SpellingModal.this, WindowEvent.WINDOW_CLOSING));
            setVisible(false);
        }
    };

    private ListSelectionListener listSelectionListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            button.setEnabled(true);
            repaint();
        }
    };


}
