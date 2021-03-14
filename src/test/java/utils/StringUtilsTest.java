package utils;

import br.com.isaquebrb.votesession.utils.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringUtilsTest {

    @Test
    public void normalizeString(){
        String voteChoice = "Não";
        String newText = StringUtils.normalize(voteChoice);
        Assertions.assertEquals("Nao", newText);
    }

    @Test
    public void hideDocument(){
        String document = "99999999999";
        String hiddenDocument = StringUtils.hideDocument(document);
        Assertions.assertEquals("XXXXXX99999", hiddenDocument);
    }
}
