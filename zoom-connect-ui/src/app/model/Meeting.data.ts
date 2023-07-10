export interface Meeting {
    agenda: string;
    created_at: Date;
    duration: number;
    host_id: string;
    id: number;
    join_url: string;
    pmi: string;
    start_time: Date;
    timezone: string;
    topic: string;
    type: number;
    uuid: string;
}