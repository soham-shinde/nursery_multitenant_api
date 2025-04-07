CREATE TABLE plot_table (
    plot_id TEXT PRIMARY KEY,
    plot_title TEXT,
    plot_description TEXT,
    inserted_at TIMESTAMP,
    is_deleted BOOLEAN DEFAULT FALSE,
    status TEXT CHECK (status IN ('ACTIVE', 'CLOSED'))
);

CREATE TABLE plot_task_table (
    plot_task_id SERIAL PRIMARY KEY,
    plot_id TEXT REFERENCES plot_table(plot_id) ON DELETE CASCADE,
    task_title TEXT,
    task_description TEXT,
    assign_to TEXT,
    worker_name TEXT,
    inserted_at TIMESTAMP,
    status TEXT CHECK (status IN ('ASSIGN', 'IN_PROGRESS', 'COMPLETED')),
    is_deleted BOOLEAN DEFAULT FALSE
);