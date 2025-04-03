package me.deshark.lms.application.cqrs.book.command;

import lombok.Data;
import me.deshark.lms.application.cqrs.core.Command;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadBookCoverCommand implements Command {
    private final String isbn;
    private final MultipartFile file;
}
