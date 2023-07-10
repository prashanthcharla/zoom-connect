import { Meeting } from "../Meeting.data";

export interface MeetingsListResponse {
    next_page_token: string;
    page_count: number;
    page_number: number;
    page_size: number;
    total_records: number;
    meetings: Array<Meeting>;
}