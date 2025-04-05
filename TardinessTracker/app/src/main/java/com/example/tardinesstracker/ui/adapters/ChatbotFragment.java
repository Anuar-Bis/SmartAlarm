override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val message = messages[position]
    holder.tvMessageText.text = message.text
    holder.tvMessageTime.text = message.time
    with(holder.cardMessage) {
        if (message.isFromBot) {
            setCardBackgroundColor(Color.parseColor("#E0E0E0"))
            holder.tvMessageText.setTextColor(Color.parseColor("#212121"))
            layoutParams = (layoutParams as ConstraintLayout.LayoutParams).apply {
                startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                endToEnd = ConstraintLayout.LayoutParams.UNSET
            }
        } else {
            setCardBackgroundColor(Color.parseColor("#3F51B5"))
            holder.tvMessageText.setTextColor(Color.parseColor("#FFFFFF"))
            layoutParams = (layoutParams as ConstraintLayout.LayoutParams).apply {
                startToStart = ConstraintLayout.LayoutParams.UNSET
                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
            }
        }
    }
}
