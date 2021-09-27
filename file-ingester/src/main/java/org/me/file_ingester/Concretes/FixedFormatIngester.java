package org.me.file_ingester.Concretes;

import org.me.file_ingester.Abstracts.Ingester;

public class FixedFormatIngester extends Ingester {
    @Override
    public void run() {
        System.out.println(path.toAbsolutePath());
    }
}
