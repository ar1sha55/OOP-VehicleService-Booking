
enum bookingStatus {
    PENDING, CONFIRMED, CANCELLED
}

class Status {
    private bookingStatus currentStatus;
    //constructor, set new booking into pending
    public Status(){
        this.currentStatus = bookingStatus.PENDING;
    }
    //set status to confirmed
    public void confirmStatus(){
        this.currentStatus = bookingStatus.CONFIRMED;
    }
    //set status to cancelled
    public void cancelStatus(){
        this.currentStatus = bookingStatus.CANCELLED;
    }

    public bookingStatus getStatus(){
        return currentStatus;
    }

    public String toString(){
        return currentStatus.toString().substring(0,1) + currentStatus.toString().substring(1).toLowerCase();
    }
}
