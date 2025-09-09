"use client";

import * as React from "react";
import { X } from "lucide-react";
import { cn } from "./utils";

export interface Toast {
  id: string;
  title?: string;
  description?: string;
  type?: "default" | "success" | "error" | "info";
  duration?: number;
}

interface ToastContextType {
  toasts: Toast[];
  toast: (toast: Omit<Toast, "id">) => void;
  dismiss: (toastId: string) => void;
}

const ToastContext = React.createContext<ToastContextType | undefined>(undefined);

export function useToast() {
  const context = React.useContext(ToastContext);
  if (!context) {
    throw new Error("useToast must be used within a ToastProvider");
  }
  return context;
}

export function ToastProvider({ children }: { children: React.ReactNode }) {
  const [toasts, setToasts] = React.useState<Toast[]>([]);

  const toast = React.useCallback((toast: Omit<Toast, "id">) => {
    const id = Math.random().toString(36).substr(2, 9);
    const newToast: Toast = {
      id,
      duration: 5000,
      ...toast,
    };

    setToasts(prev => [...prev, newToast]);

    // Auto dismiss after duration
    if (newToast.duration && newToast.duration > 0) {
      setTimeout(() => {
        dismiss(id);
      }, newToast.duration);
    }
  }, []);

  const dismiss = React.useCallback((toastId: string) => {
    setToasts(prev => prev.filter(t => t.id !== toastId));
  }, []);

  return (
    <ToastContext.Provider value={{ toasts, toast, dismiss }}>
      {children}
      <ToastContainer />
    </ToastContext.Provider>
  );
}

function ToastContainer() {
  const { toasts, dismiss } = useToast();

  return (
    <div className="fixed top-4 right-4 z-50 flex flex-col gap-2 max-w-sm w-full">
      {toasts.map(toast => (
        <ToastItem key={toast.id} toast={toast} onDismiss={dismiss} />
      ))}
    </div>
  );
}

function ToastItem({ toast, onDismiss }: { toast: Toast; onDismiss: (id: string) => void }) {
  const getToastStyles = () => {
    switch (toast.type) {
      case "success":
        return "bg-green-50 border-green-200 text-green-800";
      case "error":
        return "bg-red-50 border-red-200 text-red-800";
      case "info":
        return "bg-blue-50 border-blue-200 text-blue-800";
      default:
        return "bg-background border-border text-foreground";
    }
  };

  return (
    <div
      className={cn(
        "relative flex items-center gap-3 p-4 rounded-md border shadow-lg animate-in slide-in-from-top-full fade-in-0 duration-300",
        getToastStyles()
      )}
    >
      <div className="flex-1">
        {toast.title && (
          <div className="font-medium text-sm">{toast.title}</div>
        )}
        {toast.description && (
          <div className="text-sm opacity-90 mt-1">{toast.description}</div>
        )}
      </div>
      <button
        onClick={() => onDismiss(toast.id)}
        className="flex-shrink-0 p-1 rounded-sm opacity-70 hover:opacity-100 transition-opacity"
      >
        <X className="size-4" />
      </button>
    </div>
  );
}

// Convenience functions
export const toast = {
  success: (description: string, title?: string) => {
    const { toast: showToast } = React.useContext(ToastContext)!;
    showToast({ type: "success", description, title });
  },
  error: (description: string, title?: string) => {
    const { toast: showToast } = React.useContext(ToastContext)!;
    showToast({ type: "error", description, title });
  },
  info: (description: string, title?: string) => {
    const { toast: showToast } = React.useContext(ToastContext)!;
    showToast({ type: "info", description, title });
  },
  default: (description: string, title?: string) => {
    const { toast: showToast } = React.useContext(ToastContext)!;
    showToast({ type: "default", description, title });
  },
};