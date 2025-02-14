package org.apache.seatunnel.connectors.seatunnel.file.local.config;

import org.apache.seatunnel.api.configuration.Option;
import org.apache.seatunnel.api.configuration.Options;
import org.apache.seatunnel.connectors.seatunnel.file.config.ArchiveCompressFormat;
import org.apache.seatunnel.connectors.seatunnel.file.config.CompressFormat;
import org.apache.seatunnel.connectors.seatunnel.file.config.ExcelEngine;

public class LocalFileSourceOptions extends LocalFileBaseOptions{

    public static final Option<String> SHEET_NAME =
            Options.key("sheet_name")
                    .stringType()
                    .noDefaultValue()
                    .withDescription("To be read sheet name,only valid for excel files");

    public static final Option<ExcelEngine> EXCEL_ENGINE =
            Options.key("excel_engine")
                    .enumType(ExcelEngine.class)
                    .defaultValue(ExcelEngine.POI)
                    .withDescription("To switch excel read engine,  e.g. POI , EasyExcel");

    public static final Option<CompressFormat> COMPRESS_CODEC =
            Options.key("compress_codec")
                    .enumType(CompressFormat.class)
                    .defaultValue(CompressFormat.NONE)
                    .withDescription("Compression codec");

    public static final Option<String> FILE_FILTER_PATTERN =
            Options.key("file_filter_pattern")
                    .stringType()
                    .noDefaultValue()
                    .withDescription(
                            "File pattern. The connector will filter some files base on the pattern.");

    public static final Option<ArchiveCompressFormat> ARCHIVE_COMPRESS_CODEC =
            Options.key("archive_compress_codec")
                    .enumType(ArchiveCompressFormat.class)
                    .defaultValue(ArchiveCompressFormat.NONE)
                    .withDescription("Archive compression codec");

    public static final Option<String> NULL_FORMAT =
            Options.key("null_format")
                    .stringType()
                    .noDefaultValue()
                    .withDescription("The string that represents a null value");

    public static final Option<Boolean> PARSE_PARTITION_FROM_PATH =
            Options.key("parse_partition_from_path")
                    .booleanType()
                    .defaultValue(true)
                    .withDescription("Whether parse partition fields from file path");

    public static final Option<Long> SKIP_HEADER_ROW_NUMBER =
            Options.key("skip_header_row_number")
                    .longType()
                    .defaultValue(0L)
                    .withDescription("The number of rows to skip");
}
