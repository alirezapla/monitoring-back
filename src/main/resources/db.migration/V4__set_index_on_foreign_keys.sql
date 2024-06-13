create index doc_table_document_id_fk on doc_table (document_id);
create index computing_table_document_id_fk on computing_table_items (document_id);
create index indicator_doc_table_id_fk on indicator (doc_table_id);


