package org.me.file_ingestor.Concretes;

import org.me.file_ingestor.Abstracts.Ingestor;

public class FixedFormatIngestor extends Ingestor {
    @Override
    public void run() {
        System.out.println(path.toAbsolutePath());
    }
}
