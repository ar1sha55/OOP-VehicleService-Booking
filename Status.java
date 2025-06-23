
enum bookingStatus {
    PENDING, APPROVED, COMPLETED, CANCELLED
}

class Status {
    private bookingStatus currentStatus;

    public Status(){
        this.currentStatus = bookingStatus.PENDING;
    }

    public Status(bookingStatus newStatus){
        this.currentStatus = newStatus;
    }

    public void setStatus(bookingStatus status){
        this.currentStatus = status;
    }

    public bookingStatus getStatus(){
        return currentStatus;
    }

    public String toString(){
        return currentStatus.toString().substring(0,1) + currentStatus.toString().substring(1).toLowerCase();
    }
}
