import { RecurrenceMeeting } from "./Recurrence.request.data"
import { MeetingSettings } from "./Settings.request.data"

export interface MeetingRequest {
        topic: string
        type: number
        start_time?: string
        duration?: number
        schedule_for?: string
        timezone?: string
        password?: string
        agenda?: string
        recurrence?: RecurrenceMeeting
        settings?: MeetingSettings
}