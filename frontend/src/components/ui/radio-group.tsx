"use client";

import * as React from "react";
import { cn } from "./utils";

interface RadioGroupProps extends React.HTMLAttributes<HTMLDivElement> {
  value?: string;
  onValueChange?: (value: string) => void;
  name?: string;
}

interface RadioGroupItemProps extends React.InputHTMLAttributes<HTMLInputElement> {
  value: string;
}

const RadioGroupContext = React.createContext<{
  value?: string;
  onValueChange?: (value: string) => void;
  name?: string;
}>({});

function RadioGroup({
  className,
  value,
  onValueChange,
  name,
  children,
  ...props
}: RadioGroupProps) {
  return (
    <RadioGroupContext.Provider value={{ value, onValueChange, name }}>
      <div
        role="radiogroup"
        className={cn("grid gap-3", className)}
        {...props}
      >
        {children}
      </div>
    </RadioGroupContext.Provider>
  );
}

function RadioGroupItem({
  className,
  value,
  id,
  ...props
}: RadioGroupItemProps) {
  const context = React.useContext(RadioGroupContext);
  const inputId = id || `radio-${value}`;
  
  return (
    <div className="flex items-center">
      <input
        type="radio"
        id={inputId}
        name={context.name}
        value={value}
        checked={context.value === value}
        onChange={(e) => {
          if (e.target.checked && context.onValueChange) {
            context.onValueChange(value);
          }
        }}
        className={cn(
          "aspect-square size-4 shrink-0 rounded-full border border-input text-primary focus-visible:border-ring focus-visible:ring-ring/50 disabled:cursor-not-allowed disabled:opacity-50",
          className
        )}
        {...props}
      />
    </div>
  );
}

export { RadioGroup, RadioGroupItem };
