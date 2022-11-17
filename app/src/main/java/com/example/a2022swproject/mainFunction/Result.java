package com.example.a2022swproject.mainFunction;

public class Result<T>
{
    public static Object Success;

    private Result()
    {
    }

    @Override
    public String toString()
    {
        if (this instanceof Result.Success)
        {
            Result.Success success = (Result.Success) this;
            return "Success[data=" + success.getData().toString() + "]";
        }
        else if (this instanceof Result.Error)
        {
            Result.Error error = (Result.Error) this;
            return "Error[exception=" + error.getError().toString() + "]";
        }
        return "";
    }

    // Success
    public final static class Success<T> extends Result
    {
        private final T data;

        public Success(T data)
        {
            this.data = data;
        }

        public T getData()
        {
            return this.data;
        }
    }

    // Error
    public final static class Error extends Result
    {
        private final Exception error;

        public Error(Exception error)
        {
            this.error = error;
        }

        public Exception getError()
        {
            return this.error;
        }
    }

    // Loading
    public final static class Loading extends Result
    {
        private final String message;

        public Loading(String message)
        {
            this.message = message;
        }

        public String getMessage()
        {
            return message;
        }
    }
}
