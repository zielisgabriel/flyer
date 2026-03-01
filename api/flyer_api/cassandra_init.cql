CREATE KEYSPACE IF NOT EXISTS flyer_keyspace
WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };