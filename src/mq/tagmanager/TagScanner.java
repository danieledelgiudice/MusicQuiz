package mq.tagmanager;
import java.io.File;
import java.io.IOException;
import mq.librarymanager.Song;
import mq.librarymanager.Fields;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

/**
 * @author Daniele
 */

public class TagScanner {
    public static Song analyze(File file) throws IOException, TagException, ReadOnlyFileException,
                                          InvalidAudioFrameException, CannotReadException {
        Song song = new Song();
        Tag tag = AudioFileIO.read(file).getTag();
        song.setProperty(Fields.ARTIST, tag.getFirst(FieldKey.ARTIST));
        song.setProperty(Fields.TITLE, tag.getFirst(FieldKey.TITLE));
        song.setProperty(Fields.ALBUM, tag.getFirst(FieldKey.ALBUM));
        song.setProperty(Fields.YEAR, tag.getFirst(FieldKey.YEAR));
        song.setProperty(Fields.PATH, file.getAbsolutePath());
        return song;
    }
}
