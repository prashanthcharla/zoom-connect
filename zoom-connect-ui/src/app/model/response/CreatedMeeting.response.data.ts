import { MeetingSettings } from '../request/Settings.request.data';

export interface CreatedMeetingResponse {
    id: number;
    uuid: string;
    host_id: string;
    host_email: string;
    topic: string;
    type: string;
    status: string;
    start_time: string;
    duration: number;
    timezone: string;
    agenda: string;
    created_at: string;
    start_url: string;
    join_url: string;
    password: string;
    h323_password: string;
    pstn_password: string;
    encrypted_password: string;
    pmi: string;
    settings: MeetingSettings;
}